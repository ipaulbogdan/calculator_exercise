#!/bin/bash

#all articles (somon + rib-eye) have custom price for user Paul Idorasi 10 * 4 + 20 + 3 + 1.01
curl -X POST http://localhost:8080/calculate-basket \
   -H "Content-Type: application/json" \
   -d "{\"customerName\":\"Paul Idorasi\",\"entries\":[{\"articleName\":\"somon\",\"quantity\":4},{\"articleName\":\"rib-eye\",\"quantity\":3}]}"

echo ' '

#Popescu Ion has special price for Inghetata, but does not have special price for cartofi 2.5  * 4 + 0.89 * 3 + 1.01
curl -X POST http://localhost:8080/calculate-basket \
   -H "Content-Type: application/json" \
   -d "{\"customerName\":\"Popescu Ion\",\"entries\":[{\"articleName\":\"cartof\",\"quantity\":4},{\"articleName\":\"inghetata\",\"quantity\":3}]}"

echo ' '

# NO special price used of Paul Idorasi for products ceapa and castraveti
curl -X POST http://localhost:8080/calculate-basket \
   -H "Content-Type: application/json" \
   -d "{\"customerName\":\"Paul Idorasi\",\"entries\":[{\"articleName\":\"ceapa\",\"quantity\":4},{\"articleName\":\"castraveti\",\"quantity\":3}]}"


# expected response:
#{
#  "customerId":"customer-1",
#  "pricedBasketEntries":
#  {
#    "article-2":10.28,
#    "article-1":11.97
#  },
#  "totalAmount":23.26
#}
