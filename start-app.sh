#!/bin/sh

. /set-datasorce.sh
java -cp app:app/lib/* com.bot.checklistbot.configs.ChecklistBotApplication
