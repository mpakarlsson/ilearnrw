from lettuce import *
import requests
import base64
import mechanize

ILEARN_BASE_URL='http://api:api@api.ilearnrw.eu/ilearnrw/'
ILEARN_BASE_NO_AUTH_URL='http://api.ilearnrw.eu/ilearnrw/'
def ilurl(appendUrl, **kwargs):
    retUrl = ILEARN_BASE_URL + appendUrl
    return retUrl.format(**kwargs)
def ilurl_noauth(appendUrl, **kwargs):
    retUrl = ILEARN_BASE_NO_AUTH_URL + appendUrl
    return retUrl.format(**kwargs)

@step('the username "(.*)"')
def set_username(step, username):
    world.username = username
@step('the password "(.*)"')
def set_password(step, password):
    world.password = password
@step('the url "(.*)"')
def set_url(step, url):
    world.url = url

@step('i authenticate against api.ilearnrw.eu')
def auth(step):
    url = 'http://api:api@api.ilearnrw.eu/ilearnrw/user/auth'
    getParams = 'username={username}&pass={password}'.format(username=world.username,
                                                             password=world.password)
    world.authResult = requests.get('%s?%s' % (url, getParams)).json()
@step('i should receive a token')
def verify_token(step):
    world.authToken=world.authResult['auth']
    world.refreshToken=world.authResult['refresh']
    if len(world.authToken) == 0:
        raise Exception("Auth token invalid")
    if len(world.refreshToken) == 0:
        raise Exception("Auth token invalid")
    assert(len(base64.b64decode(world.authToken)) > 0)
    assert(len(base64.b64decode(world.refreshToken)) > 0)

@step('i get the user details')
def get_user_details(step):
    url = 'http://api:api@api.ilearnrw.eu/ilearnrw/user/details/{username}'
    url = url.format(username = world.username)
    world.userDetails = requests.get('%s?token=%s' % (url,
        world.authToken)).json()
    world.userId = world.userDetails['id']
    world.languageCode = world.userDetails['language']
@step('i get the languagecode GR')
def get_language_code_GR(step):
    assert(world.languageCode == 'GR')

@step('i classify the text "(.*)"')
def classify_english(step, text):
    url = ilurl('text/classify?userId={userid}&token={token}',
            userid=world.userId,
            token=world.authToken)
    result = requests.post(url, data=text)
    if result.status_code != 200:
        raise Exception(result.text)
    world.classificationResult = result.json()

@step('classification result "(.*)" is (\d+)')
def classifictaion_result(step, key, value):
    val = int(world.classificationResult[key])
    assert(val == int(value))

def annotate_text(text):
    url = ilurl('text/annotate?userId={userid}&lc={language}&token={token}',
            userid=world.userId,
            language=world.languageCode,
            token=world.authToken)
    result = requests.post(url, data=text)
    if result.status_code != 200:
        raise Exception(result.text)
    world.annotatedText_json = result.json()
    world.annotatedText = world.annotatedText_json["wordSet"]
    world.classificationResult = world.annotatedText
    world.annotatedText_html = world.annotatedText_json["html"]

@step('i annotate the text')
def annotate_text_multi(step):
    annotate_text(step.multiline)
@step('i annotate the text in <(.*)>')
def annotate_text_file(step, filename):
    f = open(filename, 'r')
    annotate_text(f.read())
    f.close()

@step('i annotate the text "(.*)"')
def annotate_text_single(step, text):
    annotate_text(text)

@step('i get valid xhtml')
def valid_xhtml(step):
    if len(world.annotatedText_html) < 4:
        raise Exception("Could not find valid html")
@step('wordSet "(.*)" exists')
def wordSet_key_exists(step, key):
    if not key in world.annotatedText:
        raise Exception("Key \"%s\" did not exist." % key)
@step('when i browse to "(.*)"')
def browse_to(step, url):
    world.br = mechanize.Browser()
    world.url = ilurl_noauth(url)
    print world.url
    world.br.open(world.url)

@step('i see a login form')
def check_login_form(step):
    world.br.form = list(world.br.forms())[0] #select first form
    #Check that we have the following named form items
    for n in ['username', 'pass']:
        if not n in world.br.form:
            raise Exception(n)

@step('set the form value "(.*)" to "(.*)"')
def set_form_value(step, name, value):
    world.br.form[name] = value

@step('i press Submit')
def press_submit(step):
    world.br.submit()

@step('i get redirected to "(.*)"')
def check_redirect(step, url):
    expected = ilurl_noauth(url)

    if world.br.geturl() != expected:
        raise Exception(
        '''
        Urls not the same.
        Expected: %s
        Got: %s
        ''' % (expected, world.br.geturl()))

                    
