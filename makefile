run: jar

jar: classes
	jar cfm Optimo.jar manifest.mf -C Build/ .


classes: dir
	javac -sourcepath / -d Build/ Clases/*.java

dir:
	if [ ! -d Build/ ]; then mkdir -p Build/; fi

clean:
	rm -rf Build
	rm Optimo.jar

