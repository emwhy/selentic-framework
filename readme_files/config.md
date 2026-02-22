[< Return to README](../README.md)
## Selentic Configuration

Selentic configuration file allows setting some configurations before tests start.

Selentic looks for **selentic.conf** file in a classpath. If not found, default values are used.

The following shows the default values that can be modified.

```
 // Default browser
 browser = "chrome"  // Options: chrome, firefox, safari, edge
 
 // Default headless configuration
 headless = false
 
 // Wait timeout in milliseconds
 wait-timeout-millisec = 5000
 
 // Logging configuration
 log {
     root-dir = ""              // Default is $user.dir/log. Directory where logs are stored. 
     root-log-level = "INFO"         // Log level for root logger (TRACE, DEBUG, INFO, WARN, ERROR)
     selentic-log-level = "DEBUG"      // Log level for Selentic Framework logger (TRACE, DEBUG, INFO, WARN, ERROR)
     keep-duration-min = 0           // How long to keep logs in minutes (0 = will not keep)
 }

```
