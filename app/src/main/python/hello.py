from java import jclass
import sys
import time
import imp
import threading
import inspect
import ctypes

thread1=None
Python2Java = jclass("com.matatalab.matatacode.model.Python2Java")
android = Python2Java("python")
sys.stdout=android

def _async_raise(tid, exctype):
    tid = ctypes.c_long(tid)
    if not inspect.isclass(exctype):
        exctype = type(exctype)
    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, ctypes.py_object(exctype))
    if res == 0:
        raise ValueError("invalid thread id")
    elif res != 1:
        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, None)
        raise SystemError("PyThreadState_SetAsyncExc failed")
 
def stop_thread(thread):
    _async_raise(thread.ident, SystemExit)

def thread1_run(n):
    sys.path.append(r"/data/data/com.matatalab.matatacode/run/")
    imp.load_source('mainmod', '/data/data/com.matatalab.matatacode/run/main.py')
    android.runover()
def matatalab_start(var):
    global thread1
    thread1 = threading.Thread(target=thread1_run, args=("thread1",))
    thread1.start()
    print("thread1 start")
    return;
def matatalab_stop(var):
    stop_thread(thread1)
    
    