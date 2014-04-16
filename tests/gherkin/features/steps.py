from lettuce import *
import requests
import base64

ILEARN_BASE_URL='http://api:api@api.ilearnrw.eu/ilearnrw/'
def ilurl(appendUrl, **kwargs):
    retUrl =ILEARN_BASE_URL + appendUrl
    return retUrl.format(**kwargs)

@step('the username "(.*)"')
def set_username(step, username):
    world.username = username
@step('the password "(.*)"')
def set_password(step, password):
    world.password = password

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

