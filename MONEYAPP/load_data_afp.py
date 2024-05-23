
import pandas as pd
import random
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from datetime import date, datetime



model = LinearRegression()


def load_data(path):
    df_afp = pd.read_csv(path)
    return df_afp


def fix_data(df_afp, entidad, fondo):
    df_afp = df_afp[df_afp['Nombre_Entidad'] == entidad]
    df_afp = df_afp[df_afp['Nombre_Fondo'] == fondo]
    df_afp.index = pd.to_datetime(df_afp.Fecha, dayfirst=True)
    hour = random.randint(0, 23)
    minute = random.randint(0, 59)
    second = random.randint(0, 59)
    df_afp.index = df_afp.index + pd.Timedelta(f"{hour}h") + pd.Timedelta(f"{minute}m") + pd.Timedelta(f"{second}s")
    df_afp['VALOR'] = df_afp['Valor Unidad']
    del(df_afp["Fecha"])
    del(df_afp["Codigo_Entidad"])
    del(df_afp["Codigo_Patrimonio"])
    del(df_afp["Valor Unidad"])
    del(df_afp["Nombre_Entidad"])
    del(df_afp["Nombre_Fondo"])
    createColYear(df_afp)
    createColMonth(df_afp)
    createColDay(df_afp)
    createColHour(df_afp)
    createColMinute(df_afp)
    createColSecond(df_afp)
    trainModel(df_afp)
    return df_afp


def createColYear(df_afp):
    df_afp["YEAR"] = pd.DatetimeIndex(df_afp.index).year


def createColMonth(df_afp):
    df_afp["MONTH"] = pd.DatetimeIndex(df_afp.index).month


def createColDay(df_afp):
    df_afp["DAY"] = pd.DatetimeIndex(df_afp.index).day


def createColHour(df_afp):
    df_afp["HOUR"] = pd.DatetimeIndex(df_afp.index).hour


def createColMinute(df_afp):
    df_afp["MINUTE"] = pd.DatetimeIndex(df_afp.index).minute


def createColSecond(df_afp):
    df_afp["SECOND"] = pd.DatetimeIndex(df_afp.index).second


def createGraphLineByYear(df_afp):
    df = df_afp
    df = df.sort_index(axis=0)
    return df


def createGraphLineByMonth(df_afp, y):
    df = df_afp[(df_afp['YEAR'] == y)]
    df = df.sort_index(axis=0)
    return df


def createGraphLineByDay(df_afp, y, m):
    df = df_afp[(df_afp['YEAR'] == y) & (df_afp['MONTH'] == m)]
    df = df.sort_index(axis=0)
    return df


def createGraphLineByHour(df_afp, y, m, d):
    df = df_afp[(df_afp['YEAR'] == y) & (df_afp['MONTH'] == m) & (df_afp['DAY'] == d)]
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
    data = pd.concat([data, df1], ignore_index = True)
    data.index = data['index']
    del(data['index'])
    data = data[(data['YEAR'] == y)]
    data = data.sort_index(axis=0)
    return data


def trainModel(df):
    X = df[['YEAR', 'MONTH', 'DAY']].values
    y = df['VALOR'].values
    X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=0.75, random_state=27) 
    model.fit(X_train, y_train)
    return model


def calculateInversion(y, m, d):
    prediction = model.predict([[y, m, d]])
    return prediction


