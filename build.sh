#!/bin/bash

# go into source dir
cd src || exit

# clean build
rm ui/*.class cards/*.class
javac ui/Karten.java

# create initial jar
jar cvf MischMaschine.jar cards/*.class ui/*.class img/*-64.png img/cards/*.png

# open MANIFEST file template in $EDITOR
printf "Class-Path: \nMain-Class: ui.Karten\n" > '/tmp/MANIFEST.MF'

# apply manifest to jar
jar uvfm MischMaschine.jar '/tmp/MANIFEST.MF'

# remove temporary files
rm '/tmp/MANIFEST.MF'

# moving jar file
cd ..
mv src/MischMaschine.jar .
