package me.blueysh.appman;

import lineman.Lineman;
import lineman.Logger;
import me.blueysh.appman.command.InstallCommand;
import me.blueysh.appman.command.UninstallCommand;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(Logger.Color.RED + "Usage: appman [i(nstall) | u(ninstall)] [appId]");
            return;
        }

        Lineman app = Lineman.create();
        app.addStartupHook(new Thread(() -> app.getLogger().println(Logger.Color.WHITE + "appman v1.0")));
        app.addCommand("install", new InstallCommand());
        app.addCommand("i", new InstallCommand());
        app.addCommand("uninstall", new UninstallCommand());
        app.addCommand("u", new UninstallCommand());
        app.run(args);
    }
}