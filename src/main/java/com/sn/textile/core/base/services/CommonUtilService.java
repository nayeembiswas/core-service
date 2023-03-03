package com.sn.textile.core.base.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sn.textile.core.base.constants.Constants;
import com.sn.textile.core.base.constants.ENV;
import com.sn.textile.core.base.model.MetaModel;
import com.sn.textile.core.base.model.SortModel;
import com.sn.textile.core.base.util.EnvConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */


@Service
public class CommonUtilService {

    public MetaModel getMeta(MetaModel meta, @SuppressWarnings("rawtypes") Page page) {
        if (page.hasContent()) {
            meta.setTotalRecords(page.getTotalElements());
            meta.setTotalPageCount(page.getTotalPages());
            meta.setResultCount(page.getNumberOfElements());
        }
        Integer currentOffset = meta.getPage();
        if (null != currentOffset) {
            Integer prevOffset = currentOffset - 1;
            Integer nextOffset = currentOffset + 1;
            if (prevOffset < 0) prevOffset = 0;
            if (nextOffset == page.getTotalPages()) nextOffset -= 1;
            if (page.getTotalElements() == 0) nextOffset = 0;
            meta.setPrevPage(currentOffset);
            meta.setNextPage(nextOffset);
        }
        return meta;
    }

    public Pageable getPageable(MetaModel meta) {
        if (meta == null || meta.getPage() == null || meta.getLimit() == null) return null;
        // has sorted properties inside meta
        if (null != meta.getSort() && meta.getSort().size() > 0)
            return PageRequest.of(meta.getPage(), meta.getLimit(), Sort.by(getSortOrders(meta.getSort())));
        // has no sorted properties inside meta
        return PageRequest.of(meta.getPage(), meta.getLimit());
    }

    public List<Sort.Order> getSortOrders(List<SortModel> sortModels) {
        List<Sort.Order> orders = new ArrayList<>();
        if (null != sortModels && sortModels.size() > 0)
            sortModels.stream().forEach(model -> {
                if (null != model.getField() && null != model.getOrder()
                        && !model.getField().isEmpty() && !model.getOrder().isEmpty()) {
                    orders.add(new Sort.Order(getDirection(model.getOrder()), model.getField()));
                }
            });
        orders.add(new Sort.Order(Sort.Direction.DESC, "createdOn"));
        return orders;
    }

    private Sort.Direction getDirection(String order) {
        return null != order && order.equalsIgnoreCase(Sort.Direction.DESC.toString()) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }


    public Map<String, String> getFeignHeaders(String loginToken) {
        Map<String, String> header = new HashMap<>();
        header.put("api-key", EnvConfig.getString(ENV.DEF_API_KEY, Constants.DEFAULT_API_KEY));
        header.put("api-sec", EnvConfig.getString(ENV.DEF_API_SEC, Constants.DEFAULT_API_SEC));
        header.put("Authorization", loginToken);

        return header;
    }
}
