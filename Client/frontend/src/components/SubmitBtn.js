import {useNavigation} from 'react-router-dom'

const SubmitBtn = ({text}) => {
  const navigation = useNavigation();
  const isSubmitting = navigation.state ==='submit'
  return (
    <button type="submit"
    className="capitalize tracking-wide btn btn-accent btn-block"
    disabled={isSubmitting}>
        {isSubmitting ? (
            <>
            <span className='loading loading-spinner'></span>
            Sending...
            </>
        ) : (
            text || 'submit'
        )}


    </button>
  )
}

export default SubmitBtn
