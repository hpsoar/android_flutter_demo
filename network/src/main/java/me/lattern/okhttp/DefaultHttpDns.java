package me.lattern.okhttp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;

public class DefaultHttpDns implements Dns {
    private Dns customDns;

    public DefaultHttpDns(Dns customDns) {
        this.customDns = customDns;
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (hostname == null) {
            throw new UnknownHostException("host == null");
        } else {

            if (customDns != null) {
                List<InetAddress> ips = customDns.lookup(hostname);
                if (ips != null && ips.size() > 0) {
                    return ips;
                }
            }

            //未能从httpdns获取ip地址,使用系统dns获取
            return SYSTEM.lookup(hostname);
        }
    }
}
