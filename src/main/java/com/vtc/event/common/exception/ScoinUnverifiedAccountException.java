/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.exception;

import com.vtc.event.common.ExceptionConstant;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Oct 28, 2019
 */
public class ScoinUnverifiedAccountException extends ScoinBusinessException {
    
    private static final long serialVersionUID = -3250565306799084671L;

    public ScoinUnverifiedAccountException() {
        super(ExceptionConstant.UNVERIFIED_ACCOUNT, ExceptionConstant.UNVERIFIED_ACCOUNT_DESCRIPTION);
    }
    
    public ScoinUnverifiedAccountException(String message) {
        super(ExceptionConstant.UNVERIFIED_ACCOUNT, message);
    }
    
    public ScoinUnverifiedAccountException(String objectName, String message) {
        super(ExceptionConstant.UNVERIFIED_ACCOUNT, objectName + " : " + message);
    }
}
