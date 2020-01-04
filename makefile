run: jar

jar: classes
	jar cfm funciones.jar manifest.mf -C Build/ .


classes: dir
	javac -sourcepath / -d Build/ *.java

dir:
	if [ ! -d Build/ ]; then mkdir -p Build/; fi

clean:
	rm -rf Build
	rm funciones.jar

