package space.qbpo.secured.web.one;

import org.springframework.stereotype.Service;

@Service
public class SwoService {
	public Integer addTwoInteger (Integer a, Integer b) 
		throws IllegalArgumentException {
		if (a == null || b == null)
			throw new IllegalArgumentException("parameters are not of type integer.");
		return a + b;
	}
}
