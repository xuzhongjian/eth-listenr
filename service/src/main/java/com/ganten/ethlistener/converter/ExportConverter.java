package com.ganten.ethlistener.converter;

import com.ganten.ethlistener.model.EventExport;
import com.ganten.ethlistener.model.TransferEvent;
import com.ganten.ethlistener.util.TimeUtils;

public class ExportConverter {
    public static EventExport convert(TransferEvent transferEvent) {
        EventExport eventExport = new EventExport();
        eventExport.setDate(TimeUtils.toString(transferEvent.getDate()));
        eventExport.setFrom(transferEvent.getFrom());
        eventExport.setTo(transferEvent.getTo());
        eventExport.setValue(transferEvent.getValue());

        return eventExport;
    }

    public static TransferEvent convert(EventExport eventExport) {
        TransferEvent transferEvent = new TransferEvent();
        transferEvent.setDate(TimeUtils.toDate(eventExport.getDate()));
        transferEvent.setFrom(eventExport.getFrom());
        transferEvent.setTo(eventExport.getTo());
        transferEvent.setValue(eventExport.getValue());

        return transferEvent;
    }
}
