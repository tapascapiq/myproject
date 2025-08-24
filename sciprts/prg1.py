from boto3 import resource
from boto3.dynamodb.conditions import Key,Attr
from datetime import datetime
import os


##table=resource('dynamodb').ta

dynamodb  = resource('dynamodb',
        aws_access_key_id=os.environ.get('AWS_ACCESS_KEY_ID'),
        aws_secret_access_key=os.environ.get('AWS_SECRET_ACCESS_KEY'),
        region_name=os.environ.get('AWS_REGION')
)


table=dynamodb.Table('ORDERS_TABLE')
"""
response=table.put_item(
    Item={
        'customer_id':'100',
        'order_id': '2',
        'date': str(datetime.now()),
        'customer': 'Scott'
    }
)

"""

def get_by_customer_id(cust_id):
    response=table.get_item(
        key={
            'customer_id':cust_id
        }
    )
    return response


print(get_by_customer_id('100'))