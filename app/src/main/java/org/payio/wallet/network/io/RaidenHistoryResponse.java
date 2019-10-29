package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RaidenHistoryResponse implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : SUCCESS
     * orderListVoList : [{"id":89,"sysFlowNo":"gw2018081512053000001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00326187,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00326187&sysFlowNo=gw2018081512053000001&userAddr=","orderStatus":"4","createTime":1534305930000,"updateTime":1534305955000},{"id":90,"sysFlowNo":"gw2018081512061400001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00326187,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00326187&sysFlowNo=gw2018081512061400001&userAddr=","orderStatus":"4","createTime":1534305974000,"updateTime":1534305981000},{"id":93,"sysFlowNo":"gw2018081602134100001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00326187,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00326187&sysFlowNo=gw2018081602134100001&userAddr=","orderStatus":"4","createTime":1534356821000,"updateTime":1534356825000},{"id":95,"sysFlowNo":"gw2018081606014400001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00571389,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00571389&sysFlowNo=gw2018081606014400001&userAddr=","orderStatus":"4","createTime":1534370504000,"updateTime":1534370510000},{"id":102,"sysFlowNo":"gw2018081607183900001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00276696,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00276696&sysFlowNo=gw2018081607183900001&userAddr=","orderStatus":"4","createTime":1534375119000,"updateTime":1534375125000},{"id":107,"sysFlowNo":"gw2018081607540600001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00269948,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00269948&sysFlowNo=gw2018081607540600001&userAddr=","orderStatus":"4","createTime":1534377246000,"updateTime":1534377250000},{"id":108,"sysFlowNo":"gw2018081608012600001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":2.25E-5,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00002250&sysFlowNo=gw2018081608012600001&userAddr=","orderStatus":"4","createTime":1534377686000,"updateTime":1534377732000},{"id":109,"sysFlowNo":"gw2018081608032900001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":2.25E-5,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00002250&sysFlowNo=gw2018081608032900001&userAddr=","orderStatus":"4","createTime":1534377809000,"updateTime":1534377812000},{"id":112,"sysFlowNo":"gw2018081611145600001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":5.174E-4,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00051740&sysFlowNo=gw2018081611145600001&userAddr=","orderStatus":"4","createTime":1534389296000,"updateTime":1534389302000},{"id":113,"sysFlowNo":"gw2018081611173200001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":5.174E-4,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00051740&sysFlowNo=gw2018081611173200001&userAddr=","orderStatus":"4","createTime":1534389452000,"updateTime":1534389456000},{"id":114,"sysFlowNo":"gw2018081611245600001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":5.174E-4,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00051740&sysFlowNo=gw2018081611245600001&userAddr=","orderStatus":"4","createTime":1534389896000,"updateTime":1534389901000},{"id":115,"sysFlowNo":"gw2018081611301200001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":5.174E-4,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00051740&sysFlowNo=gw2018081611301200001&userAddr=","orderStatus":"4","createTime":1534390212000,"updateTime":1534390216000},{"id":116,"sysFlowNo":"gw2018081611303400001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":5.6239E-4,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00056239&sysFlowNo=gw2018081611303400001&userAddr=","orderStatus":"4","createTime":1534390234000,"updateTime":1534390237000},{"id":146,"sysFlowNo":"gw2018082106141100001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":5.174E-4,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00051740&sysFlowNo=gw2018082106141100001&userAddr=","orderStatus":"4","createTime":1534803251000,"updateTime":1534803257000},{"id":147,"sysFlowNo":"gw2018082106145500001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00575888,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00575888&sysFlowNo=gw2018082106145500001&userAddr=","orderStatus":"4","createTime":1534803295000,"updateTime":1534803298000},{"id":149,"sysFlowNo":"gw2018082106160000001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00519649,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00519649&sysFlowNo=gw2018082106160000001&userAddr=","orderStatus":"4","createTime":1534803360000,"updateTime":1534803363000},{"id":150,"sysFlowNo":"gw2018082106161900001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00528647,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00528647&sysFlowNo=gw2018082106161900001&userAddr=","orderStatus":"4","createTime":1534803379000,"updateTime":1534803389000},{"id":159,"sysFlowNo":"gw2018082106383400001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00528647,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00528647&sysFlowNo=gw2018082106383400001&userAddr=","orderStatus":"4","createTime":1534804714000,"updateTime":1534804724000},{"id":160,"sysFlowNo":"gw2018082106391100001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00303691,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00303691&sysFlowNo=gw2018082106391100001&userAddr=","orderStatus":"4","createTime":1534804751000,"updateTime":1534804755000},{"id":161,"sysFlowNo":"gw2018082106402500001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00528647,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00528647&sysFlowNo=gw2018082106402500001&userAddr=","orderStatus":"4","createTime":1534804825000,"updateTime":1534804835000},{"id":162,"sysFlowNo":"gw2018082106434000001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00551143,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00551143&sysFlowNo=gw2018082106434000001&userAddr=","orderStatus":"4","createTime":1534805020000,"updateTime":1534805039000},{"id":181,"sysFlowNo":"gw2018082211052100001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":2.6995E-4,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00026995&sysFlowNo=gw2018082211052100001&userAddr=","orderStatus":"4","createTime":1534907121000,"updateTime":1534907128000},{"id":220,"sysFlowNo":"gw2018082308212500001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":12.16351165,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=12.16351165&sysFlowNo=gw2018082308212500001&userAddr=","orderStatus":"3","createTime":1534983685000,"updateTime":1534983709000},{"id":221,"sysFlowNo":"gw2018082309381400001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082309381400001&userAddr=","orderStatus":"3","createTime":1534988294000,"updateTime":1534988342000},{"id":222,"sysFlowNo":"gw2018082403200800001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082403200800001&userAddr=","orderStatus":"4","createTime":1535052010000,"updateTime":1535052022000},{"id":223,"sysFlowNo":"gw2018082403321500001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082403321500001&userAddr=","orderStatus":"4","createTime":1535052735000,"updateTime":1535052849000},{"id":224,"sysFlowNo":"gw2018082403355600001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082403355600001&userAddr=","orderStatus":"4","createTime":1535052956000,"updateTime":1535052957000},{"id":235,"sysFlowNo":"gw2018082505344300001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082505344300001&userAddr=","orderStatus":"4","createTime":1535146483000,"updateTime":1535146487000},{"id":297,"sysFlowNo":"gw2018082907523300001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":1.21635117,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=1.21635117&sysFlowNo=gw2018082907523300001&userAddr=","orderStatus":"4","createTime":1535500353000,"updateTime":1535500360000},{"id":298,"sysFlowNo":"gw2018082907554600001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":12.16351165,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=12.16351165&sysFlowNo=gw2018082907554600001&userAddr=","orderStatus":"4","createTime":1535500546000,"updateTime":1535500559000},{"id":299,"sysFlowNo":"gw2018082908090900001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":12.16351165,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=12.16351165&sysFlowNo=gw2018082908090900001&userAddr=","orderStatus":"4","createTime":1535501349000,"updateTime":1535501354000},{"id":300,"sysFlowNo":"gw2018082908182300001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082908182300001&userAddr=","orderStatus":"4","createTime":1535501903000,"updateTime":1535501903000},{"id":302,"sysFlowNo":"gw2018082908260900001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082908260900001&userAddr=","orderStatus":"4","createTime":1535502369000,"updateTime":1535502389000},{"id":303,"sysFlowNo":"gw2018082908272200001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":121.63511654,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=121.63511654&sysFlowNo=gw2018082908272200001&userAddr=","orderStatus":"4","createTime":1535502442000,"updateTime":1535502442000},{"id":310,"sysFlowNo":"gw2018083112090100001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018083112090100001&userAddr=","orderStatus":"4","createTime":1535688541000,"updateTime":1535688546000},{"id":312,"sysFlowNo":"gw2018090102242200001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090102242200001&userAddr=","orderStatus":"4","createTime":1535739862000,"updateTime":1535739866000},{"id":333,"sysFlowNo":"gw2018090303182900001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090303182900001&userAddr=","orderStatus":"4","createTime":1535915909000,"updateTime":1535915943000},{"id":334,"sysFlowNo":"gw2018090303544400001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090303544400001&userAddr=","orderStatus":"4","createTime":1535918084000,"updateTime":1535918102000},{"id":335,"sysFlowNo":"gw2018090303564600001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090303564600001&userAddr=","orderStatus":"4","createTime":1535918206000,"updateTime":1535918274000},{"id":336,"sysFlowNo":"gw2018090304011300001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090304011300001&userAddr=","orderStatus":"4","createTime":1535918473000,"updateTime":1535918491000},{"id":340,"sysFlowNo":"gw2018090307285000001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090307285000001&userAddr=","orderStatus":"4","createTime":1535930930000,"updateTime":1535930961000},{"id":341,"sysFlowNo":"gw2018090307305000001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090307305000001&userAddr=","orderStatus":"4","createTime":1535931050000,"updateTime":1535931073000},{"id":343,"sysFlowNo":"gw2018090406532300001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090406532300001&userAddr=","orderStatus":"4","createTime":1536015203000,"updateTime":1536015214000},{"id":344,"sysFlowNo":"gw2018090406543200001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","orderAmount":0.00122852,"payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00122852&sysFlowNo=gw2018090406543200001&userAddr=","orderStatus":"4","createTime":1536015272000,"updateTime":1536015279000}]
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("orderListVoList")
    private List<OrderListVoList> orderListVoList;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<OrderListVoList> getOrderListVoList() {
        return orderListVoList;
    }

    public void setOrderListVoList(List<OrderListVoList> orderListVoList) {
        this.orderListVoList = orderListVoList;
    }

    public static class OrderListVoList {
        /**
         * id : 89
         * sysFlowNo : gw2018081512053000001
         * tokenAddress : 0x822925476aF6C7baE9667C09161Ea84294be2500
         * receiverAddr : 0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a
         * proxyAddr : 0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20
         * customerAddr : 0x4072c97d0429c5e02824bf94681d45ea5c4428ea
         * orderAmount : 0.00326187
         * payUrl : http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.00326187&sysFlowNo=gw2018081512053000001&userAddr=
         * orderStatus : 4
         * createTime : 1534305930000
         * updateTime : 1534305955000
         */

        @SerializedName("id")
        private int id;
        @SerializedName("sysFlowNo")
        private String sysFlowNo;
        @SerializedName("tokenAddress")
        private String tokenAddress;
        @SerializedName("receiverAddr")
        private String receiverAddr;
        @SerializedName("proxyAddr")
        private String proxyAddr;
        @SerializedName("customerAddr")
        private String customerAddr;
        @SerializedName("orderAmount")
        private double orderAmount;
        @SerializedName("payUrl")
        private String payUrl;
        @SerializedName("orderStatus")
        private String orderStatus;
        @SerializedName("createTime")
        private long createTime;
        @SerializedName("updateTime")
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSysFlowNo() {
            return sysFlowNo;
        }

        public void setSysFlowNo(String sysFlowNo) {
            this.sysFlowNo = sysFlowNo;
        }

        public String getTokenAddress() {
            return tokenAddress;
        }

        public void setTokenAddress(String tokenAddress) {
            this.tokenAddress = tokenAddress;
        }

        public String getReceiverAddr() {
            return receiverAddr;
        }

        public void setReceiverAddr(String receiverAddr) {
            this.receiverAddr = receiverAddr;
        }

        public String getProxyAddr() {
            return proxyAddr;
        }

        public void setProxyAddr(String proxyAddr) {
            this.proxyAddr = proxyAddr;
        }

        public String getCustomerAddr() {
            return customerAddr;
        }

        public void setCustomerAddr(String customerAddr) {
            this.customerAddr = customerAddr;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getPayUrl() {
            return payUrl;
        }

        public void setPayUrl(String payUrl) {
            this.payUrl = payUrl;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
