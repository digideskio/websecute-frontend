source /var/my_root/repos/scripts/env.sh
sudo mongod --httpinterface --config /etc/mongodb.conf &
activator ui

Mongo queries:
db["system.indexes"].find()