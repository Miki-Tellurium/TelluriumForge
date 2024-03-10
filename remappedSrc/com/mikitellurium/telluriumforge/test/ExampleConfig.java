package com.mikitellurium.telluriumforge.test;

import com.mikitellurium.telluriumforge.TelluriumForge;
import com.mikitellurium.telluriumforge.config.ConfigEntry;
import com.mikitellurium.telluriumforge.config.EnumConfigEntry;
import com.mikitellurium.telluriumforge.config.RangedConfigEntry;
import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class ExampleConfig {
    // Create a new config builder
    public static TelluriumConfig CONFIG = new TelluriumConfig(TelluriumForge.modId());

    // Create some configs
    public static ConfigEntry<Integer> INT_CONFIG;
    public static ConfigEntry<String> STRING_CONFIG;
    public static ConfigEntry<Boolean> BOOLEAN_CONFIG;
    public static RangedConfigEntry<Integer> INT_RANGED_CONFIG;
    public static RangedConfigEntry<Double> DOUBLE_RANGED_CONFIG;
    public static RangedConfigEntry<Long> LONG_RANGED_CONFIG;
    public static ConfigEntry<String> MULTIPLE_COMMENTS_CONFIG;
    public static EnumConfigEntry<TestEnum> ENUM_CONFIG;

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
                .defineInRange("intRangedConfig", 5, 15, 10);

        DOUBLE_RANGED_CONFIG = CONFIG.entryBuilder()
                .comment("This double is always inside this range")
                .defineInRange("doubleRangedConfig", 1.0D, 2.0D, 1.2D);

        LONG_RANGED_CONFIG = CONFIG.entryBuilder()
                .comment("This long is always inside this range")
                .defineInRange("longRangedConfig", 10000L, 100000L, 25000L);

        MULTIPLE_COMMENTS_CONFIG = CONFIG.entryBuilder()
                .comment("This entry has multiple comments.")
                .comment("Here is another one.")
                .define("multipleCommentsEntry", "I have more than one comment line")
                .comment("And a third.")
                .comment("Also 4th.")
                .comment("You can comment whenever.");

        ENUM_CONFIG = CONFIG.entryBuilder()
                .comment("This is an enum")
                .define("enumConfig", TestEnum.RED)
                .comment("Test comment");

        // Build our config file, always do this for last
        CONFIG.build();
    }

    public static void onWorldPlayerJoin(Entity entity, ServerWorld world) {
        if (entity instanceof ServerPlayerEntity) {
            // Send a message on world join using the values specified in our config file
            entity.sendMessage(Text.literal("Int config is: " + ExampleConfig.INT_CONFIG.getValue()));
            entity.sendMessage(Text.literal("String config says: " + ExampleConfig.STRING_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Boolean config is: " + ExampleConfig.BOOLEAN_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Int ranged config is: " + ExampleConfig.INT_RANGED_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Double ranged config is: " + ExampleConfig.DOUBLE_RANGED_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Long ranged config is: " + ExampleConfig.LONG_RANGED_CONFIG.getValue()));
            entity.sendMessage(Text.literal("Enum config is: " + ExampleConfig.ENUM_CONFIG.getValue()));
        }
    }

}
