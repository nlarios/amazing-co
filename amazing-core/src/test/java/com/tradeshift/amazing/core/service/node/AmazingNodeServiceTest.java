package com.tradeshift.amazing.core.service.node;

import com.tradeshift.amazing.persistence.repository.HierarchyRepository;
import com.tradeshift.amazing.persistence.repository.NodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AmazingNodeServiceTest {

    @InjectMocks
    private AmazingNodeService amazingNodeService;

    @Mock
    private HierarchyRepository hierarchyRepository;

    @Mock
    private NodeRepository nodeRepository;

    @Test
    public void updateParentNodeTest() {

        assert (true);
    }

}
