package space.qbpo.secured.web.one;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/integer/")
public class SwoController {
	
	@Autowired SwoService sWOService;
	
	@RequestMapping(path="add_two/",
			produces=MediaType.APPLICATION_JSON_VALUE, 
			method=RequestMethod.GET)
	public SwoIntegerObject addTwoInteger(
			@RequestParam(name="a") Integer a, 
			@RequestParam(name="b") Integer b
			) throws IllegalArgumentException {
		return new SwoIntegerObject(sWOService.addTwoInteger(
				a, 
				b), 
				"ok");
	}
	
	public static class SwoIntegerObject {
		Integer integer;
		String status;
		
		public SwoIntegerObject(Integer i, String s) {
			this.integer = i;
			this.status = s;
		}

		public Integer getInteger() {
			return integer;
		}

		public void setInteger(Integer integer) {
			this.integer = integer;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		
	}
}
