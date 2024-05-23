
from tkinter import *
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
from load_data_afp import *
from PIL import Image, ImageTk
from datetime import date



def createFrame(root):
    global frame
    frame = Frame(root, width=850, height=550, pady=20, padx=10)
    frame.grid(row=0, column=0, sticky='nsew')
    return frame


def createPanelFrame(frame):
    global framePanel
    framePanel = Label(frame)
    framePanel.grid(row=0, column=0, padx=2, pady=0)
    return framePanel


def createControls(frame):
    global labelControls
    global txtResultInversion, txtCantInv, txtResultGan
    global txtYearInv, txtMonthInv, txtDayInv, txtResultInversion
    labelControls = Label(frame)
    labelControls.grid(row=0, column=1, padx=2, pady=0)
    labelControls.config(width=45, height=70)
    labelControlTitle = Label(labelControls, text="Panel de control", fg="black")
    labelControlTitle.grid(row=0, column=0, padx=5, pady=20, columnspan=2)
    labelControlTitle.config(width=20, font='Arial 12 bold')
    # caja de texto para la cantidad de la inversion
    labelCantInv = Label(labelControls, text="Acciones (>20): ", fg="blue")
    labelCantInv.grid(row=1, column=0, padx=1, pady=15)
    labelCantInv.config(width=20, font='Helvetica 11 bold')
    txtCantInv = Entry(labelControls, fg="red", justify=LEFT)
    txtCantInv.grid(row=1, column=1, padx=10, pady=15)
    txtCantInv.config(width=10, font='Helvetica 11 bold')
    # cajas de texto para la fecha de la inversion
    labelDate = Label(labelControls)
    labelDate.grid(row=2, column=0, padx=2, pady=0, columnspan=2)
    # labelDate.config(width=45, height=70)
    labelYearInv = Label(labelDate, text="Año", fg="blue")
    labelYearInv.grid(row=0, column=0, padx=1, pady=5)
    labelYearInv.config(width=10, font='Helvetica 11 bold')
    labelMonthInv = Label(labelDate, text="Mes", fg="blue")
    labelMonthInv.grid(row=0, column=1, padx=1, pady=5)
    labelMonthInv.config(width=10, font='Helvetica 11 bold')
    labelDayInv = Label(labelDate, text="Día", fg="blue")
    labelDayInv.grid(row=0, column=2, padx=1, pady=5)
    labelDayInv.config(width=10, font='Helvetica 11 bold')
    txtYearInv = Entry(labelDate, fg="red", justify=CENTER)
    txtYearInv.grid(row=1, column=0, padx=10, pady=5)
    txtYearInv.config(width=10, font='Helvetica 11 bold')
    txtMonthInv = Entry(labelDate, fg="red", justify=CENTER)
    txtMonthInv.grid(row=1, column=1, padx=10, pady=5)
    txtMonthInv.config(width=10, font='Helvetica 11 bold')
    txtDayInv = Entry(labelDate, fg="red", justify=CENTER)
    txtDayInv.grid(row=1, column=2, padx=10, pady=5)
    txtDayInv.config(width=10, font='Helvetica 11 bold')
    labelSepInv = Label(labelDate)
    labelSepInv.grid(row=2, column=0, padx=1, pady=10, columnspan=3)
    labelSepInv.config(width=10, font='Helvetica 11 bold')
    # Boton para calcular la inversión
    btnCalInv = Button(labelControls, text="Calcular Inversión", command=calcularInversion)
    btnCalInv.grid(row=3, column=0, columnspan=2)
    btnCalInv.config(padx=5, pady=1, width=25)
    btnCalInv.config(bg="blue2", foreground="white", font='Helvetica 10')
    # Caja de texto para el resultado de la inversion
    labelResultInversion = Label(labelControls, text="Resultado de la inversión: ", fg="blue")
    labelResultInversion.grid(row=4, column=0, padx=1, pady=20)
    labelResultInversion.config(width=20, font='Helvetica 11 bold')
    txtResultInversion = Entry(labelControls, fg="blue2", justify=CENTER)
    txtResultInversion.grid(row=4, column=1, padx=10, pady=20)
    txtResultInversion.config(width=10, font='Helvetica 11 bold')
    labelResultGan = Label(labelControls, text="Ganancia/Perdida: ", fg="blue")
    labelResultGan.grid(row=5, column=0, padx=1, pady=0)
    labelResultGan.config(width=20, font='Helvetica 11 bold')
    txtResultGan = Entry(labelControls, fg="red", justify=CENTER)
    txtResultGan.grid(row=5, column=1, padx=10, pady=0)
    txtResultGan.config(width=10, font='Helvetica 11 bold')
    labelFinal = Label(labelControls)
    labelFinal.grid(row=6, column=0, padx=1, pady=20, columnspan=2)
    labelFinal.config(width=20, height=5)


def createTitle(frame):
    labelTitle = Label(frame, text="Indicadores Económicos", fg="black")
    labelTitle.grid(row=0, column=0, padx=5, pady=10, columnspan=9)
    labelTitle.config(width=20, font='Arial 18 bold')


def createMenuOptions(frame, entidad, fondo):
    global txtYear, txtMonth
    # Indicadores
    labelListInd = Label(frame, text="Indicador: ", fg="black")
    labelListInd.grid(row=1, column=0, padx=2, pady=15)
    labelListInd.config(width=10, font='Helvetica 11 bold')
    listInd = Label(frame, text=f"AFP: {entidad}", fg="black")
    listInd.grid(row=1, column=1, padx=2, pady=15)
    listInd.config(width=35, foreground="blue2", font='Helvetica 11 bold')
    # Label para el indicador de fondo
    labelFond = Label(frame, text=fondo, fg="black")
    labelFond.grid(row=2, column=0, padx=5, pady=5, columnspan=9)
    labelFond.config(width=50, font='Helvetica 11 bold')
    # caja de texto para el año
    # Label para la separación de controles
    labelSep = Label(frame)
    labelSep.grid(row=1, column=2, padx=5, pady=15)
    labelSep.config(width=20)
    # caja de texto para el año
    labelYear = Label(frame, text="Año: ", fg="blue")
    labelYear.grid(row=1, column=3, padx=1, pady=15)
    labelYear.config(width=4, font='Helvetica 11 bold')
    txtYear = Entry(frame, fg="red", justify=LEFT)
    txtYear.grid(row=1, column=4, padx=5, pady=15)
    txtYear.config(width=8, font='Helvetica 11 bold')
    # caja de texto para el mes
    labelMonth = Label(frame, text="Mes: ", fg="blue")
    labelMonth.grid(row=1, column=5, padx=1, pady=15)
    labelMonth.config(width=4, font='Helvetica 11 bold')
    txtMonth = Entry(frame, fg="red", justify=LEFT)
    txtMonth.grid(row=1, column=6, padx=5, pady=15)
    txtMonth.config(width=8, font='Helvetica 11 bold')
    # Label para la separación de controles
    labelSep_1 = Label(frame)
    labelSep_1.grid(row=1, column=7, padx=2, pady=15)
    labelSep_1.config(width=2)
    # Botones para el ver la grafica ajustada por parametros
    btnSee = Button(frame, text="Ver", command=createGraphByParam)
    btnSee.grid(row=1, column=8)
    btnSee.config(padx=5, pady=2, width=10)
    btnSee.config(bg="blue2", foreground="white", font='Helvetica 10')


def createPanel(frame):
    global labelPanel
    labelPanel = Label(frame)
    labelPanel.grid(row=3, column=0, padx=2, pady=5, columnspan=9)
    labelPanel.config(width=900, height=450)
    return labelPanel


def createGraphic(frame, labelPanel, data):
    fig = Figure(figsize=(9,4))
    plot = fig.add_subplot(1, 1, 1)
    plot.plot(data.index, data['VALOR'])
    plot.set_xlabel("Tiempo")
    plot.set_ylabel("Valor")
    plot.tick_params(axis='both', which='major', labelsize=7)
    plot.grid(True)
    canvas = FigureCanvasTkAgg(fig, master=frame)
    canvas.draw()
    img = ImageTk.PhotoImage(Image.frombytes("RGB", canvas.get_width_height(), canvas.tostring_rgb()))
    labelPanel.config(image=img)
    labelPanel.image = img


def message(textMsg):
    newRoot = Toplevel()
    newRoot.title("Message")
    msgText = Label(newRoot, text=textMsg)
    msgText.grid(row=0, column=0, padx=20, pady=20)
    msgText.config(width=50, height=5, foreground="blue2", font='Helvetica 10 bold')
    msgText.pack()


def createGraphByParam():
    year = txtYear.get()
    month = txtMonth.get()
    if(year and month):
        data = createGraphLineByDay(data_, int(year), int(month))
        createGraphic(framePanel, labelPanel, data)
    elif(year):
        data = createGraphLineByMonth(data_, int(year))
        createGraphic(framePanel, labelPanel, data)
    else:
        message("Debe ingresar un año o mes")


def showFrameCol(root, path, entidad, fondo):
    global path_
    path_ = path
    frame = createFrame(root)
    frame.tkraise()
    framePanel = createPanelFrame(frame)
    createControls(frame)
    createTitle(framePanel)
    createMenuOptions(framePanel, entidad, fondo)
    labelPanel = createPanel(framePanel)
    loadData(framePanel, labelPanel, path_, entidad, fondo)


def loadData(frame, labelPanel, path, entidad, fondo):
    global data_, model
    data_ = load_data(path)
    data_ = fix_data(data_, entidad, fondo)
    df = createGraphLineByYear(data_)
    createGraphic(frame, labelPanel, df)


def calcularInversion():
    cantidadInv = txtCantInv.get()
    year = txtYearInv.get()
    month = txtMonthInv.get()
    day = txtDayInv.get()
    if(not len(cantidadInv) or not len(year) or not len(month) or not len(day)):
        message('Debe rellenar todos los campos')
    elif(float(cantidadInv) > 20):
        dateToday = date.today()
        year_today = dateToday.year
        month_today = dateToday.month
        day_today = dateToday.day
        if(int(year) > year_today):
            resultInvToday = calculateInversion(int(year_today), int(month_today), int(day_today))
            inversion = float(cantidadInv) * float(resultInvToday)
            resultInv = calculateInversion(int(year), int(month), int(day))
            resultInv_ = float(resultInv) * float(cantidadInv)
            ganancia = resultInv_ - inversion
            txtResultInversion.insert(INSERT, round(resultInv_, 2))
            txtResultGan.insert(INSERT, round(ganancia, 2))
            data = addDataframe(data_, int(year), int(month), int(day))
            createGraphic(framePanel, labelPanel, data)
        else:
            message('El año ingresado debe ser mayor al actual')
    else:
        message('Debe ingresar una cantidad de acciones mayor a 20')


