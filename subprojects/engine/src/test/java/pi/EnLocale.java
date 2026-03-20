package pi;

import java.util.Locale;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EnLocale implements AfterEachCallback, BeforeEachCallback
{
    private Locale originalDefault;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        originalDefault = Locale.getDefault();
        Locale.setDefault(new Locale("en", "US"));
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception
    {
        Locale.setDefault(originalDefault);
    }
}
