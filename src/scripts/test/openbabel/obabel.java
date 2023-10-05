package scripts.test.openbabel;

import org.apache.commons.exec.*;

import java.io.IOException;

/*
IMPORTANT!!!
MAKE SURE ENVIRONMENT VARIABLES ARE SET CORRECTLY. THEY SHOULD LOOK SIMILAR TO:
...
CONDA_EXE=${HOME}/anaconda3/bin/conda
CONDA_PREFIX=${HOME}/anaconda3
CONDA_PYTHON_EXE=${HOME}/anaconda3/bin/python
PATH=${HOME}/anaconda3/bin:${HOME}/anaconda3/condabin:${HOME}/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/snap/bin
...
 */

public class obabel {
    public static void run() throws IOException, InterruptedException {
        CommandLine cmdLine = new CommandLine("obabel");
        cmdLine.addArgument("-V");
//        cmdLine.addArgument("/h");
//        cmdLine.addArgument("${file}");
//        HashMap map = new HashMap();
//        map.put("file", new File("invoice.pdf"));
//        cmdLine.setSubstitutionMap(map);

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);
        executor.setWatchdog(watchdog);
        executor.execute(cmdLine, resultHandler);

        // some time later the result handler callback was invoked, so we
        // can safely request the exit value
        resultHandler.waitFor();
        int exitValue = resultHandler.getExitValue();
        System.out.printf("exit %d", exitValue);
    }
    public static void main(String[] args) {
        obabel test = new obabel();
        try {
            test.run();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
