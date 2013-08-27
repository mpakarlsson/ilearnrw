/**
 * 
 * \addtogroup OnlineResourceBankTodo
 * 	@{
 * 		@todo: Could a resource consist of multiple items of the same type?
 *  @}
 */

 /**  
 * ##Purpose
 * 
 * Excerpt from DOW ILearnRW (318803) 2012-06-22.pdf
 * Page 49, point #7
 * 
 * > On-line resource bank. A learning tool is useless when no appropriate learning
 * > material is available for it. The learning material should be readily available
 * > and accessible from everywhere, i.e., it should be on-line. The material should
 * > contain not only "isolated" titles of text. It should contain coherent
 * > collections of data which support specific teaching strategies and constitute
 * > well structured learning/intervention programs, forming a resource bank valuable
 * > to learners, educators and dyslexia professionals. The resource bank should be
 * > also to offer its customized views of its material to each learner, based on the
 * > learner's profile, i.e., the resource bank should be profile-sensitive. That
 * > means, that the user will be able to browse the resource bank and view its
 * > content sorted according to the suitability/ appropriateness to her profile. In
 * > addition, the user should be able to issue search requests with her profile as
 * > one search argument, making in this way the reading activities more enjoyable.
 * 
 * 
 * ##Requirements
 * 
 * ###Needed resources
 * 
 * * A server.
 * * A database.
 * 
 * ###Required libraries
 * 
 * ##Functionality
 * 
 * The Online Resource Bank contains a number of `Resources` and a number of `Teaching strategies`.
 * The `Teaching strategies` maps `UserProblems` to `Resources`.
 * 
 * A `Resource` combines multiple `DataResources` such as text, images and audio into a single resource.
 * 
 * \image latex ilearnrwOnlineResourcebank.png "Online Resource Bank" width=16cm
 * 
 * ###RESTful API
 * 
 * The Online Resource Bank is designed to be a RESTful service.
 * This means that no state is ever stored at the server and standard
 * HTTP headers are utilized.
 * 
 * The clients connect to the service using standard HTTP. Since some
 * information sent to the server encryption should always be enabled.
 * 
 * ##Authorization
 * 
 * A cookie named `Token` would have to be set to authenticate the current
 * user.
 * 
 * 		Cookie: Token=<token>
 * 
 * ###Token
 * 
 * The token would be one of 2 things
 * 
 * 1. An id in a lookup table on the Authorization server
 * 2. Signed user information (with potential extra information)
 * 
 * Option 1 would require the Online Resource Bank to contact the authorization server
 * to verify the token.
 * In option 2 as long as the signature can be verified the token can be trusted to be valid.
 * 
 * The token could be stored in a cookie on the client and sent to the server in each request.
 * 
 * Tokens are excluded from the rest of the documentation but must be available in the implementation.
 * 
 * ##API
 * 
 * Note that how to administer the system has been ignored at the moment.
 * 
 * ###Browse
 * 
 * 		GET /browse?userProfile=<userProfile>&page=<pageNumber>&SupportedContentTypes=plain%2Ftext%2C%20audio%2Fmpeg
 * 
 * Returns a list of resources suited for the particular user.
 * Note that we need to know what content types the client can handle.
 * Accepted format is `application/json`
 * 
 * GET of browse:
 * 
 * 		GET /browse?userProfile=<userProfile>&page=1&SupportedContentTypes=plain%2Ftext%2C%20audio%2Fmpeg
 * 
 * Response:
 * 		
 * 		{
 * 			"page": 1,
 * 			"result": [
 * 				{
 * 					"name": "Resource 1",
 * 					"description": "Resource 1 description",
 * 					"score": 100
 * 					"data": [
 * 								["plain/text; charset=\"utf8\"", 1233],
 * 								["audio/mpeg", 19393949],
 * 							]
 * 				},
 * 				... (more resource objects)
 * 		}
 * 
 * ###Resources
 * 
 * 		GET /resource/<resource-id>
 * 		GET /resource/<resource-id>/data
 * 
 * Identifies the resource itself. This could be a piece of text, an audio clip etc.
 * GET of resource:
 * 		
 * 		GET /resource/<resource-id>
 * 		Accept: application/json
 * 
 * Response:
 * 
 * 		ContentType: application/json
 * 		
 * 		{
 * 			"name": "Resource 1",
 * 			"description": "Resource 1 description",
 * 			"data": [
 * 						["plain/text; charset=\"utf8\"", 1233],
 * 						["audio/mpeg", 19393949],
 * 						["image/png", 29393]
 * 					]
 * 		}
 * 						
 * To fetch the data of the resource in audio format:
 * 
 * 		GET /Resource/<resource-id>/data
 * 		Accept: audio/mpeg
 * 
 * Response:
 * 
 * 		HTTP/1.1 200 OK
 * 		Content-Type: audio/mpeg
 * 		Content-Length: xxx
 * 
 * Alternatively:
 * 
 * 		HTTP/1.1 302
 * 		Location: http://someServer/audio/someclip.mp3
 * 
 * Implementations for CREATE, PUT, POST and DELETE would allow administrative tasks to be done
 * via an application or a web interface. Although the CREATE would just use `/Resource` as URI.
 * 
 */
package ilearnrw.online.resourcebank;

public interface IResourceBank {

}
