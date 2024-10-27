package com.neoris.transactions.client.mapper;

import com.neoris.transactions.client.entity.AccountEntity;
import com.neoris.transactions.vo.AccountVo;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 27/10/2024
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountVo toAccountVo(AccountEntity account);

    Collection<AccountVo> toAccountVoCollection(Collection<AccountEntity> accounts);

    AccountEntity toAccount(AccountVo accountVo);

}
