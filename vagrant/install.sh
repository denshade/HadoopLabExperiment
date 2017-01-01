set -x
sudo apt-get install openjdk-7-jdk -y
sudo addgroup hadoop
sudo adduser --ingroup hadoop hduser
su - hduser
mkdir /home/hduser/.ssh
echo | ssh-keygen -t rsa -P ""
 cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys
ssh localhost # to accept the fingerprint.

# Disabling IPv6 by adjusting hadoop-env.sh
mkdir ~/hadoopinstall
cd ~/hadoopinstall

wget http://www-eu.apache.org/dist/hadoop/core/stable/hadoop-2.7.3.tar.gz
tar xzf hadoop-*.tar.gz

as root cntrl D
cd /home/hduser
sudo mv hadoop-*/ /usr/local/hadoop
sudo chown -R hduser:hadoop /usr/local/hadoop
cp /vagrant/bashrc.hadoop ~
grep hadoop "~/.bashrc"
if [ $? -ne 0 ]; then
  echo "source bashrc.hadoop" >> ~/.bashrc
fi

echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64" >> /usr/local/hadoop/etc/hadoop/hadoop-env.sh
sudo mkdir -p /app/hadoop/tmp
sudo chown -R hduser:hadoop /app/hadoop/tmp
sudo chmod 750 /app/hadoop/tmp

cp /vagrant/hdfs-site.xml /usr/local/hadoop/etc/hadoop/hdfs-site.xml
cp /vagrant/core-site.xml /usr/local/hadoop/etc/hadoop/core-site.xml
cp /vagrant/mapred-site.xml /usr/local/hadoop/etc/hadoop/mapred-site.xml

/usr/local/hadoop/bin/hadoop namenode -format

/usr/local/hadoop/sbin/start-dfs.sh
/usr/local/hadoop/sbin/start-yarn.sh
