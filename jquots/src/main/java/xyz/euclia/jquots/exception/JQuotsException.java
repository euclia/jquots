/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.euclia.jquots.exception;

/**
 *
 * @author pantelispanka
 */
public class JQuotsException extends RuntimeException {

    public JQuotsException() {
    }

    public JQuotsException(String message) {
        super(message);
    }

    public JQuotsException(String message, Throwable cause) {
        super(message, cause);
    }

    public JQuotsException(Throwable cause) {
        super(cause);
    }
    
}
