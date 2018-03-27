package com.skoo.stock.login;

import com.skoo.stock.login.domain.Ticket;
import com.skoo.stock.util.ManUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RecoverTicket implements Runnable {

    private static final Log logger = LogFactory.getLog(RecoverTicket.class);
    private Map<String, Ticket> tickets;

    public RecoverTicket(Map<String, Ticket> tickets) {
        super();
        this.tickets = tickets;
    }

    @Override
    public void run() {
        // not redis do
        if (!ManUtil.isRedis()) {
            List<String> ticketKeys = new ArrayList<>();
            for (Entry<String, Ticket> entry : tickets.entrySet()) {
                if (entry.getValue().getRecoverTime().getTime() < System.currentTimeMillis())
                    ticketKeys.add(entry.getKey());
            }
            for (String ticketKey : ticketKeys) {
                tickets.remove(ticketKey);
                logger.debug("ticket[" + ticketKey + "]过期已删除！");
                //System.out.println("ticket[" + ticketKey + "]过期已删除！");
            }
        }
    }

}
