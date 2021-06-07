## 删除索引
```
DELETE /tag
```

## 创建索引
```
PUT /tag
```


## 创建mapping

```
PUT /tag/_doc/_mapping?pretty
 {
      "_doc": {
        "properties": {
          "channel": {
            "type": "text",
            "fields": {
              "keyword": {
                "type": "keyword"
              }
            }
          },
          "chargeMoney": {
            "type": "float"
          },
          "couponTimes": {
            "type": "date"
          },
          "favGoods": {
            "type": "text",
            "fields": {
              "keyword": {
                "type": "keyword"
              }
            }
          },
          "feedBack": {
            "type": "long"
          },
          "freeCouponTime": {
            "type": "date"
          },
          "memberId": {
            "type": "text",
            "fields": {
              "keyword": {
                "type": "keyword"
              }
            }
          },
          "orderCount": {
            "type": "long"
          },
          "orderMoney": {
            "type": "float"
          },
          "orderTime": {
            "type": "date"
          },
          "overTime": {
            "type": "long"
          },
          "phone": {
            "type": "text",
            "fields": {
              "keyword": {
                "type": "keyword"
              }
            }
          },
          "regTime": {
            "type": "date"
          },
          "sex": {
            "type": "text",
            "fields": {
              "keyword": {
                "type": "keyword"
              }
            }
          },
          "subOpenId": {
            "type": "text",
            "fields": {
              "keyword": {
                "type": "keyword"
              }
            }
          }
        }
      }
    }
```

## 查看mapping
```
GET /tag/_mapping
```

## 查看数据
```
GET /tag/_search
{
  # query dsl json here
}
```

## 删除具体一条数据(_id)
```
DELETE /tag/_doc/l2Zqb28BzvITasB-oTzB
```