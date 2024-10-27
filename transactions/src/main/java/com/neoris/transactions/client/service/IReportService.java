package com.neoris.transactions.client.service;

import com.neoris.transactions.vo.AccountStatusVo;
import com.neoris.transactions.vo.DateRangeVo;

import java.util.Collection;

/**
 * @author jyepez on 27/10/2024
 */
public interface IReportService {

    DateRangeVo buildDateRangeVo(String startDate, String endDate);

    Collection<AccountStatusVo> accountStatus(DateRangeVo dateRange, String identification);

}
