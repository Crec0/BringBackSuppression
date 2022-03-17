# Bring back suppression

A simple mod to bring back update suppression.

Patched by mojang in 22w11a.

RIP StackOverflowError. You will be remembered.

## How does it work

With the new server properties option, you can set the max amount of updates to process before they are skipped.  
Game will skip updates after that point.
This mod uses that threshold value as the target point and will throw exception if it is exceeded.  
This exception is later caught in the main server loop, therefore the game will not crash and still suppress updates successfully.

If the threshold is set to -1, the mod will set the threshold value at 8000.

For an ideal setup, set the threshold to be 8000 to 10000. This will be roughly equivalent to how many updates it used to send in earlier versions.
