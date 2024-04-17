import {useNavigation} from 'react-router-dom'

const SubmitBtn = ({text , icon}) => {
  const navigation = useNavigation();
  const isSubmitting = navigation.state ==="submitting"
  return (
    <button type="submit"
    className="capitalize tracking-wide btn  btn-accent btn-block"
    disabled={isSubmitting}>
        {isSubmitting ? (
            <>
            <span className='loading loading-spinner bg-primary'></span>
            Sending...
            </>
        ) : (
            
            text || 'submit'
        )}


    </button>
  )
}

export default SubmitBtn
