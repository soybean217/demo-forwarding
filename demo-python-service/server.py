# coding: utf-8

import tornado.ioloop
import tornado.web
import struct

testContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
testContent += "<wml>"
testContent +="<card>"
testContent +="<Ccmd_cust>3</Ccmd_cust>"
testContent +=  "<Cnum_cust>13590100473</Cnum_cust>"
testContent +=  "<filter1_cust>test中文123|媒体互动|057128812163|中国移动</filter1_cust>"
testContent +=  "<filter2_cust>test中文123|媒体互动</filter2_cust>"
testContent +=  "<Creconfirm_cust>本次密码*，输入</Creconfirm_cust>"
testContent +=  "<fee>2</fee>"
testContent +=  "<autofee>305 </autofee>"
testContent +=  "<feemode>11</feemode>"
testContent +=  "<popu>您将选择使用由xx公司提供的手机定位业务，5元包月，点击确认开始享受该服务，退出则不付费 。客服电话：0755-83506715</popu>"
testContent +=  "</card>" 
testContent +=  "<br/><Cname>37.536146,121.380833</Cname>"
testContent +=  "<br/><CAddress>中国山东省烟台市芝罘区文化三巷</CAddress> "
testContent +=  "<br/><AddressDetails Accuracy=\"6\"> </AddressDetails>"
testContent +=  "<br/><coordinates>121.380833,37.536146,0</coordinates>" 
testContent +=  "</wml>";

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("Hello, world")
    def post(self, *args, **kwargs):
        print(self.request.body)
        print(struct.unpack('<B',self.request.body[0]))
        print('type:')
        print(struct.unpack('<B',self.request.body[31]))
#         print(struct.unpack('<B',self.request.body[1]))
        print('customCode-Custcode:'+self.request.body[32:47]);
        print('projectCode-ProCode:'+self.request.body[48:63]);
        print('imsi:'+self.request.body[64:80]);
        print('smsCenter:'+self.request.body[81:96]);
        print("decode end")
        self.write(testContent)

def make_app():
    return tornado.web.Application([
        (r"/", MainHandler),
        (r"/tcd/", MainHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(38765)
    tornado.ioloop.IOLoop.current().start()