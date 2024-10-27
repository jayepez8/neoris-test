package com.neoris.transactions.client.mapper;

import com.neoris.transactions.client.common.DateUtil;
import com.neoris.transactions.client.entity.MovementEntity;
import com.neoris.transactions.vo.MovementVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

/**
 * @author jyepez on 27/10/2024
 */
@Mapper(componentModel = "spring",uses = DateUtil.class)
public interface MovementMapper {


    @Mapping(source = "date", target = "date", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "account.accountType", target = "accountType")
    MovementVo toMovementVo(MovementEntity movement);

    Collection<MovementVo> toMovementVoCollection(Collection<MovementEntity> movement);

    MovementEntity toMovement(MovementVo movementVo);

}
