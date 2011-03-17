import java.io.BufferedReader;
import java.io.InputStreamReader;

import play.Logger;
import play.Play;
import play.PlayPlugin;

public class GitPlugin extends PlayPlugin {

	private static String GIT_PLUGIN_PREFIX = "GIT plugin: ";

	@Override
	public void onApplicationStart()  {
		Logger.info(GIT_PLUGIN_PREFIX + "executing 'git describe'");
		final StringBuffer gitVersion = new StringBuffer();
		try {
			final Process p = Runtime.getRuntime().exec("git describe"); 
			final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 

			// wait for process to complete
			p.waitFor(); 
			
			// read the output
			String line = reader.readLine(); 
			while(line != null) { 
				gitVersion.append(line); 
				line = reader.readLine(); 
			} 
		}
		catch(Exception e) {
			Logger.error(GIT_PLUGIN_PREFIX + "unable to execute 'git describe'");
		}
		
		// set a property for this value
		Play.configuration.setProperty("git.revision", gitVersion.toString());

		Logger.info(GIT_PLUGIN_PREFIX + "revision is " + gitVersion.toString());
	}
}
