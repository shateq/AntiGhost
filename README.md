<img src="src/main/resources/logo.png" width="256">

# AntiGhost
_Ghost blocks are blocks that seem to be air to the client, but are solid to the server. They occur when the server and client are out of sync; in many cases, this happens when a haste effect wears off and there's some network latency between both. 
The server tells the client the haste effect is gone, but the client doesn't know about this yet and sends block break events to the server at full speed. 
The server thinks the client is cheating, so it doesn't break the blocks; the client doesn't know that, so it thinks the blocks are air now. When the player tries moving into those blocks, the server kicks him back.
I've seen this most often on servers that have MCMMO enabled, when super breaker runs out, but it happens when beacon effects are ending as well.

Generally, the only way to fix this is re-logging, or porting away and back, to unload and re-load the chunks. This mod asks the server to resend all blocks around the player when you type /ghost, within a 4 block radius cube, thus making re-logging unneccesary. Or, just press the G key._

**Fabric API is required!**

## Download
[![latest-builds](https://github.com/shateq/AntiGhost/actions/workflows/gradle.yml/badge.svg)](https://github.com/shateq/AntiGhost/actions/workflows/gradle.yml)

[GitHub Releases](https://github.com/shateq/AntiGhost/releases) |
---
### License
Check `LICENSE` file for more details.