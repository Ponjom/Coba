.PHONY: build push

build:
	docker build -t swr.ru-moscow-1.hc.sbercloud.ru/lily/l-telegram-channel-message:latest .

push:
	docker push swr.ru-moscow-1.hc.sbercloud.ru/lily/l-telegram-channel-message:latest
	docker rmi swr.ru-moscow-1.hc.sbercloud.ru/lily/l-telegram-channel-message:latest

all: build push