var IE=document.all?true:false;
function BodyEventsManager(name,fun){}
BodyEventsManager.eventsMap={};
BodyEventsManager.push=function(name,fun)
{
	if(fun!=null)
	{
		if(IE)
		{
			if(document.body[name]!=null)
			BodyEventsManager._getEvents(name).push(document.body[name]);
			document.body[name]=fun;
		}
		else
		{
			if(window[name]!=null)
			BodyEventsManager._getEvents(name).push(window[name]);
			window[name]=fun;
		 }
	 }
}
BodyEventsManager._getEvents=function(name)
{
	if(BodyEventsManager.eventsMap[name]==null)
	BodyEventsManager.eventsMap[name]=new Array();
	return BodyEventsManager.eventsMap[name];
}
BodyEventsManager.pop=function(name)
{
	if(BodyEventsManager.eventsMap[name]!=null&&BodyEventsManager.eventsMap[name].length>0)
	{
		if(IE)document.body[name]=BodyEventsManager.eventsMap[name].pop();
		else window[name]=BodyEventsManager.eventsMap[name].pop();
	}
}