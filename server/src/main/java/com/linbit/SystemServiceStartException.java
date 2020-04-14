package com.linbit;

import com.linbit.linstor.LinStorException;

/**
 * Thrown to indicate that a service failed to start
 *
 * @author Robert Altnoeder &lt;robert.altnoeder@linbit.com&gt;
 */
public class SystemServiceStartException extends LinStorException
{
    private static final long serialVersionUID = -4577443321214547238L;

    public final boolean criticalError;

    public SystemServiceStartException(String message, boolean criticalErrorRef)
    {
        super(message);
        criticalError = criticalErrorRef;
    }

    public SystemServiceStartException(String message, Throwable cause, boolean criticalErrorRef)
    {
        super(message, cause);
        criticalError = criticalErrorRef;
    }

    public SystemServiceStartException(
        String message,
        String descriptionText,
        String causeText,
        String correctionText,
        String detailsText,
        boolean criticalErrorRef
    )
    {
        super(message, descriptionText, causeText, correctionText, detailsText, null);
        criticalError = criticalErrorRef;
    }

    public SystemServiceStartException(
        String message,
        String descriptionText,
        String causeText,
        String correctionText,
        String detailsText,
        Throwable cause,
        boolean criticalErrorRef
    )
    {
        super(message, descriptionText, causeText, correctionText, detailsText, cause);
        criticalError = criticalErrorRef;
    }
}
