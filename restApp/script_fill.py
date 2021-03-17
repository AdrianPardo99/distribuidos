#!/usr/bin/env python3
# @author Adrian Gonzalez Pardo
import pyautogui, time
time.sleep(5)
pyautogui.typewrite("inicio_correo")
pyautogui.hotkey('@')
pyautogui.typewrite("gmail.com")
pyautogui.press("tab")
text=["Nombre","Ap_P","Ap_M","Birth","Tel","M o F"]
for word in text:
    pyautogui.typewrite(word)
    pyautogui.press("tab")
