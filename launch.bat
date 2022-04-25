@echo off
	javac -encoding utf8 -d classes src/hu/tapasztaltak/model/*.java src/hu/tapasztaltak/proto/*.java
	if not exist classes mkdir classes
	cd classes
	jar cfe ../tapasztaltak.jar hu.tapasztaltak.proto.ProtoMain hu/tapasztaltak
	cd ..
	java -jar tapasztaltak.jar