
import pandas as pd
import random
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from datetime import date, datetime



df_dolar = pd.read_csv('dataset/dolar_price.csv')
model = LinearRegression()


def fix_data():
    df_dolar.index = pd.to_datetime(df_dolar.VIGENCIADESDE, dayfirst=True)
    hour = random.randint(0, 23)
    minute = random.randint(0, 59)
    second = random.randint(0, 59)
    df_dolar.index = df_dolar.index + pd.Timedelta(f"{hour}h") + pd.Timedelta(f"{minute}m") + pd.Timedelta(f"{second}s")
    del(df_dolar["VIGENCIADESDE"])
    del(df_dolar["VIGENCIAHASTA"])
    df_dolar['VALOR'] = df_dolar['VALOR'].str.replace(',', '').astype(float)


def createColYear():
    df_dolar["YEAR"] = pd.DatetimeIndex(df_dolar.index).year


def createColMonth():
    df_dolar["MONTH"] = pd.DatetimeIndex(df_dolar.index).month


def createColDay():
    df_dolar["DAY"] = pd.DatetimeIndex(df_dolar.index).day


def createColHour():
    df_dolar["HOUR"] = pd.DatetimeIndex(df_dolar.index).hour


def createColMinute():
    df_dolar["MINUTE"] = pd.DatetimeIndex(df_dolar.index).minute


def createColSecond():
    df_dolar["SECOND"] = pd.DatetimeIndex(df_dolar.index).second


def createGraphLineByYear():
    df = df_dolar
    df = df.sort_index(axis=0)
    return df


def createGraphLineByMonth(y):
    df = df_dolar[(df_dolar['YEAR'] == y)]
    df = df.sort_index(axis=0)
    return df


def createGraphLineByDay(y, m):
    df = df_dolar[(df_dolar['YEAR'] == y) & (df_dolar['MONTH'] == m)]
    df = df.sort_index(axis=0)
    return df


def createGraphLineByHour(y, m, d):
    df = df_dolar[(df_dolar['YEAR'] == y) & (df_dolar['MONTH'] == m) & (df_dolar['DAY'] == d)]
    df = df.sort_index(axis=0)
    return df


def addDataframe(data, y, m, d):
    val = []
    yy = []
    mm = []
    dd = []
    indx = []
    dateToday = date.today()
    y_now = dateToday.year
    m_now = dateToday.month
    d_now = dateToday.day
    while(True):
        pred = calculateInversion(y_now, m_now, d_now)
        val.append(float(pred))
        yy.append(y_now)
        mm.append(m_now)
        dd.append(d_now)
        indx.append(datetime(y_now, m_now, d_now))
        d_now += 1
        if(d_now > 28):
            d_now = 1
            m_now += 1
        if(m_now > 12):
            m_now = 1
            y_now +=1
        if((y == y_now) and (m == m_now) and (d == d_now)):
            break
    Info = {'index': indx, 'VALOR': val, 'UNIDAD': 'COP', 'YEAR': yy, 'MONTH': mm, 'DAY': dd}
    df1 = pd.DataFrame(Info)
    data['index'] = data.index
    df_ = pd.concat([data, df1], ignore_index = True)
    df_.index = df_['index']
    del(df_['index'])
    df_ = df_[(df_['YEAR'] == y)]
    df_ = df_.sort_index(axis=0)
    return df_


def trainModel():
    print(df_dolar.head())
    X = df_dolar[['YEAR', 'MONTH', 'DAY']].values
    y = df_dolar['VALOR'].values
    X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=0.75, random_state=27) 
    model.fit(X_train, y_train)
    return model


def calculateInversion(y, m, d):
    prediction = model.predict([[y, m, d]])
    return prediction

