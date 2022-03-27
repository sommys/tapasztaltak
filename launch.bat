@echo off
	javac -encoding utf8 -d classes src/hu/tapasztaltak/model/*.java src/hu/tapasztaltak/skeleton/*.java
	cd classes
	jar cfe ../tapasztaltak.jar hu.tapasztaltak.skeleton.SkeletonMenu hu/tapasztaltak
	cd ..
	java -jar tapasztaltak.jar