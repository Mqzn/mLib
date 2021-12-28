package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.MCommand;
import org.bukkit.entity.Player;

public class TestCommand extends MCommand {

    public TestCommand() {
        super("test", "perm.test", false, "test2");
    }

    @Override
    public void setInfo() {

        this.setDefaultActions(((sender, args) -> {
            Player player = (Player)sender;

            //SkinData data = new SkinData("ewogICJ0aW1lc3RhbXAiIDogMTYwMTQxNDc3ODI0MCwKICAicHJvZmlsZUlkIiA6ICJhMjk1ODZmYmU1ZDk0Nzk2OWZjOGQ4ZGE0NzlhNDNlZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJWaWVydGVsdG9hc3RpaWUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNmOWFiN2MxNjE1ZWY4NDVhYzUzYTYyMTI3NmZiMzZkODI1NjRlOWY4YmRhNWRjMWM4MDg1NjRlZTVjYjUzZCIKICAgIH0KICB9Cn0=",
                    //"g9ft0oIiagz3BqYwReHDbjgH841x6Xfk4jPGenO2hGUXg24mRtkJIbaurZD+HiY62SfUdttOv4H/DAwQIKrtD1741Xs+7hck6VidZT0EW68MQV2wb68A67BHligiCQuDnmTE7bn3LBfiKGCOlhUpVuxRqg057qvig3VUQwzkHTiBsG5jROIsYgen7SlbT+2xW67ws+GoqNrA6sdUTOPtGYotB3dE2xracVqoXDQkG4WtAmMuYWBU7iNQbYCUA1q7pbNf/5Vwh45C9RGkPJXqSbU3IoFvI5MweONEZHmPSL02QwiKz2XsU64cHKTMrERX1sQIshY6tVNvUdAaqyfiYESKpGoYO2kTCsOeO/TX4jzNN9eMrWPEfDQy7JV+2xF3csP0hNYU8YdQ45ZC3/sfL9R+Tn9o8tmcFZNFnWVtZM/ixdroHbo857YrmNxIjd5PaxCYTMv50LQ3nF4la/HS9Zw97Bawm32YGOSEM7jnNQGJDukcAK5fZ1bFNzr8EW1+4PCHXW6fI9kEItRr//UzNnIgNPktQYkn6UxJVRgIrzs3huMlx3Ys0r9zcSGZ3IauJfvoxx3y2QU0u5OslXw4ljirqVgV6Sbk5J6POriX+gicnTchGDoynWmcH4hBH9QNNbRI0WQQcd6jZOA2eujh73J0BWG38bAm5Wt3IceHn7k=");

        }));

    }

}
