# TMC BM Helper Mod

keeps **Wave Health** bossbar in sync with wave mobs, meant to be used together with the datapack. you control *when* modes change; the mod handles the bar updates every tick.

---

## What it does

When the server starts, the mod automatically:

1. **Creates a bossbar** named `Wave Health` (red bar, id `tmc.dev.wave_health`).
2. **Shows the bar to all online players** (and updates the list when players join or leave).
3. **Creates a scoreboard** used to switch between two modes: **ready** and **charging**.
4. **Updates the bossbar** based on the current mode and on mobs that have a specific tag (see below).

You do **not** need to run `/bossbar add` yourself. The mod sets that up once per server session.

### Which mobs count?

Only living entities (mobs, bosses, etc.) with the command tag:

```text
tmc.dev.summoned_entity
```

Add it the usual way, for example:

```mcfunction
/tag @e[type=zombie,limit=1] add tmc.dev.summoned_entity
```

Untagged mobs are ignored. Health is added up across **all dimensions** (Overworld, Nether, End, etc.).

---

## The two modes

The mod reads a score on the scoreboard. You change the mode by changing that score (commands, datapack, buttons, etc.).

| Mode       | Score value | What the bossbar does |
|-----------|-------------|------------------------|
| **Ready** | `1` (default) | **Value** = total **current health** of all tagged mobs. The **max** does not change in this mode. |
| **Charging** | `0` | **Max** rises as tagged mobs are summoned (sum of their max health). **Value** fills up slowly (+1 per game tick) until it reaches the max. Max is never set to 0. |

Think of **charging** as “the bar is filling up before the wave,” and **ready** as “the bar shows how much health the wave has left.”
It emulates the raid thing. 

### Scoreboard details (for commands)

| Item | Name |
|------|------|
| Objective | `tmc.dev.bossbar_mode` |
| Holder (fake player) | `#tmc.dev.bossbar` |

On first setup, the mod sets the score to **1** (**ready**).

### Switching modes manually

Run these as op or from server console:

**Charging** (fill up phase):

```mcfunction
scoreboard players set #tmc.dev.bossbar tmc.dev.bossbar_mode 0
```

**Ready** (track live wave health):

```mcfunction
scoreboard players set #tmc.dev.bossbar tmc.dev.bossbar_mode 1
```

Any score other than `0` is treated as **ready**.

---

## Bossbar reference

| Setting | Id / name |
|---------|-----------|
| Bossbar id | `tmc.dev.wave_health` |
| Display name | Wave Health |
| Color | Red |

You can still use vanilla `/bossbar` commands for things the mod does not touch (name, color, visibility, etc.), but the mod will keep overwriting **value** and **max** according to the rules above while the server is running.

