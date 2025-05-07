package com.mikitellurium.telluriumforge.example;

import com.mikitellurium.telluriumforge.TelluriumForge;
import com.mikitellurium.telluriumforge.config.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ExampleConfig {
    // Create a new config builder
    public static final TelluriumConfig CONFIG = new TelluriumConfig(TelluriumForge.modId(), TelluriumConfig.Type.COMMON);

    // Create some configs
    public static NumberConfigEntry<Integer> INT_CONFIG;
    public static StringConfigEntry STRING_CONFIG;
    public static BooleanConfigEntry BOOLEAN_CONFIG;
    public static RangedConfigEntry<Integer> INT_RANGED_CONFIG;
    public static RangedConfigEntry<Double> DOUBLE_RANGED_CONFIG;
    public static RangedConfigEntry<Long> LONG_RANGED_CONFIG;
    public static StringConfigEntry MULTIPLE_COMMENTS_CONFIG;
    public static EnumConfigEntry<Direction> ENUM_CONFIG;
    public static ConfigEntry<BlockPos> POS_CONFIG;

    // This will be called on mod init
    public static void buildConfig() {
        // Add comments to the file that will be written at the top
        CONFIG.comment("TelluriumConfig Example")
                .comment("")
                .comment("This is an example on how to use the TelluriumConfig class");

        // Define the configs, the comment is optional
        INT_CONFIG = CONFIG.entryBuilder()
                .define("intConfig", 20);

        STRING_CONFIG = CONFIG.entryBuilder()
                .define("stringConfig", "Hello Minecraft!");

        BOOLEAN_CONFIG = CONFIG.entryBuilder()
                .comment("Is this true or false?")
                .define("booleanConfig", true);

        INT_RANGED_CONFIG = CONFIG.entryBuilder()
                .comment("This integer is always inside this range")
                .defineInRange("intRangedConfig", 5, 2, 20);

        DOUBLE_RANGED_CONFIG = CONFIG.entryBuilder()
                .comment("This double is always inside this range")
                .defineInRange("doubleRangedConfig", 1.2D, 1.0D, 2.0D);

        LONG_RANGED_CONFIG = CONFIG.entryBuilder()
                .comment("This long is always inside this range")
                .defineInRange("longRangedConfig", 30000L, 25000L, 100000L);

        MULTIPLE_COMMENTS_CONFIG = CONFIG.entryBuilder()
                .comment("This entry has multiple comments.")
                .comment("Here is another one.")
                .define("multipleCommentsEntry", "I have more than one comment line")
                .comment("And a third.")
                .comment("Also a 4th.")
                .comment("You can comment whenever.");

        ENUM_CONFIG = CONFIG.entryBuilder()
                .comment("This is an enum")
                .define("directionConfig", Direction.NORTH)
                .comment("Test comment");

        POS_CONFIG = CONFIG.entryBuilder()
                .comment("This config holds a blockPos")
                .define(new ConfigEntry<>(CONFIG, BlockPos.class, "posConfig", new BlockPos(5, 10, 7)) {
            @Override
            public String serialize(BlockPos value) {
                return value.toShortString();
            }

            @Override
            public BlockPos deserialize(String string) {
                String[] strings = string.split(",", 3);
                int x = Integer.parseInt(strings[0].trim());
                int y = Integer.parseInt(strings[1].trim());
                int z = Integer.parseInt(strings[2].trim());
                return new BlockPos(x, y, z);
            }
        });

        // Build our config file, always do this for last
        CONFIG.build();
    }

}
