view.post{}

首先判断mAttachInfo是不是null,
赋值地点view.dispatchAttachedToWindow()
不为空，用mAttachInfo.getRunQueue()记录，后续dispatchAttachedToWindow()调用时，
mRunQueue.executeActions(info.mHandler);

也就是说，view.post{},
最终一定执行在mAttachInfo.Handler对应的消息队列中