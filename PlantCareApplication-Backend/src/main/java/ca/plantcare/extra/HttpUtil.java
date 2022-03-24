package ca.plantcare.extra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Provides methods to make controller stuff easier.
 */
public class HttpUtil {

	/**
	 * Return a response entity for a successful http request with the specified
	 * object as the body of the response.
	 * @param httpResponseBody
	 * @return
	 */
	public static ResponseEntity<?> httpSuccess(Object httpResponseBody) {
		return ResponseEntity.status(HttpStatus.OK).body(httpResponseBody);
	}
	
	/**
	 * Return a response entity for a failed http request with the specified
	 * object as the body of the response.
	 * @param httpResponseBody
	 * @return
	 */
	public static ResponseEntity<?> httpFailure(Object httpResponseBody) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseBody);
	}
	
	/**
	 * Returns a ResponseEntity object with status code 400 and body which is a
	 * String of the form ("Error: " + {@code eMsg}).
	 * @param errorMessage error message to display.
	 * @return
	 */
	public static ResponseEntity<?> httpFailureMessage(String errorMessage) {
		return httpFailure("Error: " + errorMessage);
	}
	
	/**
	 * This doesn't work like I thought it would...
	 * Returns an array containing the input string as well as the input string
	 * with an extra '/' appended. Use for controller methods to map endpoint to 
	 * both url with and without trailing backslash '/'.
	 * @param url Endpoint url
	 * @return endpoint url and endpoint url with trailing backslask '/'
	 */
	public static String[] urlDupe(String url) {
		String urlWithBackSlash = url.concat("/");
		return new String[] {url, urlWithBackSlash};
	}
}

