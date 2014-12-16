package fi.attemoisio.songbookapi.exceptions;

/*
 * ###################################################################=
 * Songbook API
 * %%
 * Copyright (C) 2014 Atte Moisio
 * %%
 * The MIT License (MIT)
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 * ###################################################################-
 */

import fi.attemoisio.songbookapi.errorhandling.ApiError;

public class ApiException extends RuntimeException{

	private static final long serialVersionUID = -4145100011024195143L;
	
	private ApiError error;

	public ApiError getApiError() {
		return error;
	}
	
	public ApiException() {}
	
	public ApiException(ApiError error) {
		super(error.getDescription());
		this.error = error;
	}
	
	public ApiException(ApiError error, Throwable cause) {
		super(error.getDescription(), cause);
		this.error = error;
	}

}
