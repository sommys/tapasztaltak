@echo off
	if not exist classes mkdir classes
	javac -encoding utf8 -d classes src/hu/tapasztaltak/model/*.java src/hu/tapasztaltak/proto/*.java src/hu/tapasztaltak/view/*.java
	cd classes
	jar cfe ../tapasztaltak.jar hu.tapasztaltak.model.Game hu/tapasztaltak
	cd ..
	java -jar tapasztaltak.jar