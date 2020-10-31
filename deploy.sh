#!/bin/bash

heroku login
heroku container:login
heroku container:push bot -a java-checklist-bot
heroku container:release bot -a java-checklist-bot
