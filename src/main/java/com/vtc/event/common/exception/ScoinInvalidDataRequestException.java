/***************************************************************************
 * Product 2018 by Quang Dat * 
 **************************************************************************/
package com.vtc.event.common.exception;

import com.vtc.event.common.ExceptionConstant;

/**
 * Creator : Le Quang Dat
 * Email   : quangdat0993@gmail.com
 * Date    : Oct 11, 2018
 */
public class ScoinInvalidDataRequestException extends ScoinBusinessException {
    
    private static final long serialVersionUID = -6502596312985405760L;

    public ScoinInvalidDataRequestException() {
        super(ExceptionConstant.INVALID_DATA_REQUEST, ExceptionConstant.INVALID_DATA_REQUEST_DESCRIPTION);
    }
    
    public ScoinInvalidDataRequestException(String message) {
        super(ExceptionConstant.INVALID_DATA_REQUEST, message);
    }
    
    public ScoinInvalidDataRequestException(String objectName, String message) {
        super(ExceptionConstant.INVALID_DATA_REQUEST, objectName + " : " + message);
    }

}
