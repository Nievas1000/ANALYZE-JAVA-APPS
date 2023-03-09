#!/bin/bash
chmod 400 testApp.pem
ssh -tt -o "StrictHostKeyChecking=no" -i "testApp.pem" ubuntu@54.237.66.158 'sudo update-alternatives --set java /usr/lib/jvm/java-17-openjdk-amd64/bin/java; chmod +x ./script.sh; sudo ./script.sh'