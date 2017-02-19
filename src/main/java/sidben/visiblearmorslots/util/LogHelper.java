package sidben.visiblearmorslots.util;

import java.util.IllegalFormatException;
import org.apache.logging.log4j.Level;
import net.minecraftforge.fml.common.FMLLog;
import sidben.visiblearmorslots.main.ModConfig;
import sidben.visiblearmorslots.main.Reference;



public class LogHelper
{

    private static void log(Level logLevel, String format, Object... data)
    {
        try {
            FMLLog.log(Reference.MOD_ID, logLevel, format, data);
        } catch (final IllegalFormatException e) {
            System.out.println(e);
            System.out.println(format);
        }
    }

    public static void error(String format, Object... data)
    {
        log(Level.ERROR, format, data);
    }

    public static void warn(String format, Object... data)
    {
        log(Level.WARN, format, data);
    }

    public static void info(String format, Object... data)
    {
        log(Level.INFO, format, data);
    }

    public static void debug(String format, Object... data)
    {
        if (ModConfig.onDebug()) {
            log(Level.DEBUG, format, data);
        }
    }

    public static void debug(Object object)
    {
        debug(String.valueOf(object), new Object[0]);
    }

    public static void trace(String format, Object... data)
    {
        if (ModConfig.onDebug()) {
            log(Level.TRACE, format, data);
        }
    }

    public static void trace(Object object)
    {
        trace(String.valueOf(object), new Object[0]);
    }

}